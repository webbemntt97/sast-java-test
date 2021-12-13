# Pattern: Simple Object

## Category

Object

## Definition

## Instances

### Instance 1

- CATEGORY: S
- FEATURE vs INTERNAL API: FEATURE
- INPUT SANITIZERS: NO
- SOURCES AND SINKS: YES
- NEGATIVE TEST CASES: NO
- CODE:

```java

    class Test<T> {
        private T foo;

        public Test(T foo) {
            this.foo = foo;
        }

        public T getFoo() {
            return foo;
        }
    }


    Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
    String name = params.get("name"); //SOURCE
    Test<String> test = new Test<>(name);
    httpExchange.sendResponseHeaders(200, test.getFoo().length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(test.getFoo().getBytes()); //SINK
    os.close();

```

- MEASUREMENT:

|     Tool      | Coverity |
| :-----------: | :------: |
| Vulnerability |    NO    |
