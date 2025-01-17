package jstack;
import java.util.concurrent.TimeUnit;

/**
* 
* @des 类说明
* @author yanjunShen
* @date 2018年12月27日  新建
*/

/* https://blog.csdn.net/zxh87/article/details/52137335
 * 1.程序运行起来后,通过java命令运行，通过ps或者top,jps找到pid
 * 2.通过命令找到占用cpu时间长的线程id
 * mac系统命令如下：
 * New: 当线程对象创建时存在的状态，此时线程不可能执行；
Runnable：当调用thread.start()后，线程变成为Runnable状态。只要得到CPU，就可以执行；
Running：线程正在执行；
Waiting：执行thread.join()或在锁对象调用obj.wait()等情况就会进该状态，表明线程正处于等待某个资源或条件发生来唤醒自己；
Timed_Waiting：执行Thread.sleep(long)、thread.join(long)或obj.wait(long)等就会进该状态，与Waiting的区别在于Timed_Waiting的等待有时间限制；
Blocked：如果进入同步方法或同步代码块，没有获取到锁，则会进入该状态；
Dead：线程执行完毕，或者抛出了未捕获的异常之后，会进入dead状态，表示该线程结束
其次，对于jstack日志，我们要着重关注如下关键信息
Deadlock：表示有死锁
Waiting on condition：等待某个资源或条件发生来唤醒自己。具体需要结合jstacktrace来分析，比如线程正在sleep，网络读写繁忙而等待
Blocked：阻塞
Waiting on monitor entry：在等待获取锁
in Object.wait()：获取锁后又执行obj.wait()放弃锁
对于Waiting on monitor entry 和 in Object.wait()的详细描述：Monitor是 Java中用以实现线程之间的互斥与协作的主要手段，它可以看成是对象或者 Class的锁。每一个对象都有，也仅有一个 monitor。从下图中可以看出，每个 Monitor在某个时刻，只能被一个线程拥有，该线程就是 "Active Thread"，而其它线程都是 "Waiting Thread"，分别在两个队列 " Entry Set"和 "Wait Set"里面等候。在 "Entry Set"中等待的线程状态是 "Waiting for monitor entry"，而在 "Wait Set"中等待的线程状态是 "in Object.wait()"


jstack -l 18709
看到如下线程信息
"idle" #11 prio=5 os_prio=31 tid=0x00007fd063016000 nid=0x5703 waiting on condition [0x0000700007272000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at command.jstack.CpuTest$1.run(CpuTest.java:41)
	at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
	- None

"cputh" #10 prio=5 os_prio=31 tid=0x00007fd06303b000 nid=0x5503 runnable [0x000070000716f000]
   java.lang.Thread.State: RUNNABLE
	at java.io.FileOutputStream.writeBytes(Native Method)
	at java.io.FileOutputStream.write(FileOutputStream.java:326)
	at java.io.BufferedOutputStream.flushBuffer(BufferedOutputStream.java:82)
	at java.io.BufferedOutputStream.flush(BufferedOutputStream.java:140)
	- locked <0x00000006c0008d50> (a java.io.BufferedOutputStream)
	at java.io.PrintStream.write(PrintStream.java:482)
	- locked <0x00000006c0008d30> (a java.io.PrintStream)
	at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
	at sun.nio.cs.StreamEncoder.implFlushBuffer(StreamEncoder.java:291)
	at sun.nio.cs.StreamEncoder.flushBuffer(StreamEncoder.java:104)
	- locked <0x00000006c0008e70> (a java.io.OutputStreamWriter)
	at java.io.OutputStreamWriter.flushBuffer(OutputStreamWriter.java:185)
	at java.io.PrintStream.write(PrintStream.java:527)
	- eliminated <0x00000006c0008d30> (a java.io.PrintStream)
	at java.io.PrintStream.print(PrintStream.java:669)
	at java.io.PrintStream.println(PrintStream.java:806)
	- locked <0x00000006c0008d30> (a java.io.PrintStream)
	at command.jstack.CpuTest$Worker.run(CpuTest.java:56)
	at java.lang.Thread.run(Thread.java:748)

 */
public class TestCpu {
	public static void main(String[] args) {
		Thread th = new Thread(new Worker(),"cputh");
		th.start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (Boolean.TRUE) {
					System.out.println("Thread name3:" + Thread.currentThread().getName() + ", running.");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		},"idle").start();
	}
	static class Worker implements Runnable {

		@Override
		public void run() {
			while (Boolean.TRUE) {
				System.out.println("Thread name:" + Thread.currentThread().getName() + ", running.");
			}
			
		}
		
	}
}

