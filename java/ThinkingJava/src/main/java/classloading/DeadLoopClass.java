package classloading;
/**
* 
* @des 虚拟机会保证<clinit>() 方法线程安全 
* @author yanjunShen
* @date 2018年12月16日  新建
*/

public class DeadLoopClass {
	static {
		/*如果不加上这个if,编译器将会提示"Initializer does not complete normally"
		 * 并拒绝编译
		 * */
		if (Boolean.TRUE) {
			System.out.println(Thread.currentThread() + " init DeadLoopClass.");
			while (Boolean.TRUE) {
				
			}
		}
	}
	public static void main(String[] args) {
		Runnable script = new Runnable() {
			public void run () {
				System.out.println(Thread.currentThread() + " start.");
				DeadLoopClass dlc = new DeadLoopClass();
				System.out.println(Thread.currentThread() + " run over.");
			}
		};
		Thread th1 = new Thread(script);
		Thread th2 = new Thread(script);
		th1.start();
		th2.start();
	}
}
