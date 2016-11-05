package cz.jalasoft.calculator.parser;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class aggregates all registered listeners of parsing and exposes
 * the listener interface via method {@link #proxy()}, when the parser
 * invokes any method on such "dynamic" listener, the proxy delegates the
 * invocation to all the registered "regular" listeners.
 *
 * The reason for this solution is (except love for dynamic proxies) the
 * ability to avoid using the same code pattern - invocation of all
 * listeners in a cycle.
 *
 * If an exception is thrown by one or more regular listeners, the proxy
 * throws a runtime exception that has suppressed exceptions thrown by the
 * regular listeners.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
final class AggregatingInstructionListener {

    private final ArithmeticInstructionsParserListener proxy;
    private final ListenerMethodInvocationHandler handler;

    AggregatingInstructionListener() {
        handler = new ListenerMethodInvocationHandler();
        proxy = (ArithmeticInstructionsParserListener) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { ArithmeticInstructionsParserListener.class }, handler);
    }

    void addListener(ArithmeticInstructionsParserListener listener) {
        handler.addListener(listener);
    }

    ArithmeticInstructionsParserListener proxy() {
        return proxy;
    }

    //-------------------------------------------------------------
    //PROXY HANDLER
    //-------------------------------------------------------------

    private final class ListenerMethodInvocationHandler implements InvocationHandler {

        private final Collection<ArithmeticInstructionsParserListener> listeners = new ArrayList<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RuntimeException exception = new RuntimeException("An error has occurred in listener of arithmetic operation during parsing.");

            listeners.forEach(l -> {
                try {
                    method.invoke(l, args);
                } catch (ReflectiveOperationException exc) {
                    exception.addSuppressed(exc);
                }
            });

            if (exception.getSuppressed().length > 1) {
                throw exception;
            }

            return null;
        }

        void addListener(ArithmeticInstructionsParserListener listener) {
            listeners.add(listener);
        }
    }
}
