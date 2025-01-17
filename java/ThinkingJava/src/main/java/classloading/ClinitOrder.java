package classloading;
/**
* 
* @des 说明<clinit>() 方法执行顺序,先执行父类在执行子类
* @author yanjunShen
* @date 2018年12月16日  新建
*/

public class ClinitOrder {
	static class Parent {
		public static int A = 1;
		static {
			A = 2;
		}
	}
	static class Sub extends Parent {
		public static int B = A;
	}
	public static void main(String[] args) {
		System.out.println(Sub.B);
	}
}
