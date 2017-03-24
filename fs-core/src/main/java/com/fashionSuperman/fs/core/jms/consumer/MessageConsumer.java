package com.fashionSuperman.fs.core.jms.consumer;

public interface MessageConsumer<S,T> {
	S consume(T t);
}
