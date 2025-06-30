import java.util.*;
import java.time.*;

class PacketBucket {
    Queue<Integer> queue = new LinkedList<>();
    int drainRate;
    int maxCapacity;

    public PacketBucket(int rate, int capacity) {
        drainRate = rate;
        maxCapacity = capacity;
    }

    public synchronized void insertPacket(int size) {
        if (size <= maxCapacity) {
            queue.add(size);
            maxCapacity -= size;
            System.out.println("Packet added. Available space: " + maxCapacity);
        } else {
            System.out.println("Overflow! Dropped: " + (size - maxCapacity));
            queue.add(maxCapacity);
            maxCapacity = 0;
        }
    }

    public synchronized int processPackets(int size) {
        if (!queue.isEmpty() || size > 0) {
            if (size < drainRate && !queue.isEmpty()) {
                while (size <= drainRate && !queue.isEmpty())
                    size += queue.poll();
            }
            int processed = Math.min(drainRate, size);
            maxCapacity += processed;
            System.out.println("Processed: " + processed + ". Remaining space: " + maxCapacity);
            size -= processed;
            if (queue.isEmpty() && size == 0) {
                System.out.println("Queue empty. Remaining space: " + maxCapacity);
            }
        }
        return size;
    }
}

class Worker implements Runnable {
    PacketBucket bucket;
    Scanner scanner = new Scanner(System.in);
    int mode;

    public Worker(PacketBucket bucket, int mode) {
        this.bucket = bucket;
        this.mode = mode;
    }

    @Override
    public void run() {
        if (mode == 2) {
            int size = 0;
            while (true) {
                size = bucket.processPackets(size);
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
            }
        } else {
            LocalTime start = LocalTime.now();
            while (true) {
                int size = scanner.nextInt();
                LocalTime end = LocalTime.now();
                Duration gap = Duration.between(start, end);
                if (gap.toSeconds() != 0) {
                    size *= gap.toSeconds();
                }
                System.out.println("Time gap (s): " + gap.toSeconds() + " Modified size: " + size);
                bucket.insertPacket(size);
                start = end;
            }
        }
    }
}

public class LeakControl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Define Bucket Capacity:");
        int capacity = scanner.nextInt();
        System.out.println("Define Drain Rate:");
        int rate = scanner.nextInt();
        
        PacketBucket bucket = new PacketBucket(rate, capacity);
        System.out.println("Start entering values:");
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        
        Thread producer = new Thread(new Worker(bucket, 1));
        Thread consumer = new Thread(new Worker(bucket, 2));
        
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (Exception e) {
        }
    }
}