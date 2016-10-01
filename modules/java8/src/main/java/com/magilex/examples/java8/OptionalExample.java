package com.magilex.examples.java8;

import java.util.Optional;

import static java.lang.System.*;

public class OptionalExample {
	
	static Optional<MyClass> opt = Optional.empty(); // Empty is initial state
	
	public static void main(String[] args) {
		out.println("1. OPTIONAL of Empty");
		//out.println(opt.get()); //throws java.util.NoSuchElementException
		out.println("toString(): " + opt.toString());
		out.println("isPresent(): " + opt.isPresent());
		out.println("orElse(): " + opt.orElse(new MyClass("OtherString")));
		out.println(opt.flatMap(MyClass::getObj1).orElse("orElse() Functional, obj1 not present"));
		opt.ifPresent( x -> out.print("Print  optional if present " + x) );
		out.println("");
		
		opt = Optional.of(new MyClass("MyString"));
		out.println("2. OPTIONAL of MyClass[String]");
		out.println(opt.get());
		out.println("toString(): " + opt.toString());
		out.println("isPresent(): " + opt.isPresent());
		out.println("orElse(): " + opt.orElse(new MyClass("OtherString")));
		opt.ifPresent( x -> out.println("isPresent(): if present print this: " + opt) );
		out.println("");
		
		opt = Optional.of(new MyClass());
		out.println("3. OPTIONAL of MyClass[EmptyString]");
		out.println(opt.get());
		out.println("toString(): " + opt.toString());
		out.println("isPresent(): " + opt.isPresent());
		out.println("orElse(): " + opt.orElse(new MyClass("OtherString")));
		out.println("orElseGet() 1: " + opt.flatMap(x -> x.getObj1()).orElseGet(() -> new MyClass("This is a funky thang!")));
		out.println("orElseGet() 2: " + opt.flatMap(MyClass::getObj1).orElseGet(() -> returnFunkyVersionOfMyClass()));
		out.println("orElse() in opt.obj1: " + opt.flatMap(MyClass::getObj1).orElse("OtherThing"));
		opt.ifPresent( x -> out.println("Print  optional if present " + opt) );
		out.println("");
		
		opt = Optional.empty();
		opt.orElseThrow(IllegalStateException::new);

	}
	
	static MyClass returnFunkyVersionOfMyClass() { return new MyClass("This is a funky thang!"); }

	public static class MyClass {
		Optional<Object> obj1 = Optional.empty();

		public MyClass() {}
		
		public MyClass(Object obj1) {
			super();
			this.obj1 = Optional.of(obj1);
		}
		
		public Optional<Object> getObj1() {
			return obj1;
		}
		
		public MyClass myFunkyVersion() { return new MyClass("This is a funky thang!"); }
		
		@Override
		public String toString() {
			return "MyClass [obj1=" + obj1 + "]";
		}
		
	}

}
