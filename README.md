# Tema03---Lab-PAOJ

# Project Overview

This repository contains four independent Java exercises demonstrating advanced object‐oriented programming, I/O, random access files, and functional programming with streams and lambdas.

---

## 1. Product Record System

**Context**  
Managing a binary file `products.dat` containing serialized objects of type `Product` (attributes: `String name`, `double price`, `int stock`).

**Specific Requirements**  
1. **Class `Product`**  
   - Implements `Serializable`  
   - Validates that `price` and `stock` cannot be negative; if invalid, throws a checked `InvalidDataException`  
2. **Writing to File**  
   - Write **10** `Product` instances to `products.dat` using `ObjectOutputStream` in a _try-with-resources_ block  
   - Catch any `IOException` and log errors to `errors.log` via `PrintWriter`  
3. **Reading from File**  
   - Read back all products using `ObjectInputStream`  
   - Handle `EOFException` (end of file) and `ClassNotFoundException`  
4. **Stream Processing**  
   - Create a `Stream<Product>` from the read data  
   - **Filter** products with zero stock and save them to `depleted.txt`  
   - **Map**: reduce each product’s stock by 10% using a `UnaryOperator<Product>`  
   - **Reduce**: find and display the product with the highest price using `max(Comparator)`

---

## 2. Advanced Order Processing

**Context**  
Manage customer orders in the binary file `orders.dat`, using random access.

**Specific Requirements**  
1. **Class `Order`**  
   - Attributes: `int id`, `String customerName`, `double value`, `boolean finalized`  
   - Implements `Serializable`  
2. **Initial Serialization**  
   - Write **15** `Order` objects to `orders.dat` via `ObjectOutputStream`  
3. **Updating Records**  
   - Open `orders.dat` with `RandomAccessFile` in `"rw"` mode  
   - For all orders where `value > 5000`, set `finalized = true`  
   - Handle all possible exceptions: `EOFException`, `IOException`, `SecurityException`  
4. **Post-Update Processing**  
   - Read updated orders back into `List<Order>`  
   - Use Streams to:  
     - **Filter** only finalized orders  
     - **Sum** the values of all finalized orders  
     - **Group** finalized orders by `customerName` using `Collectors.groupingBy`

---

## 3. Customer Processing System

**Context**  
Process a list of customers using complex functions and streams.

**Specific Requirements**  
1. **Class `Client`**  
   - Attributes: `String name`, `int age`, `double sumAccount`, `Optional<String> clientType` (values: `VIP`, `Standard`, `New`)  
2. **Data Initialization**  
   - Create a `List<Client>` with at least **12** diverse entries  
3. **Functional Stream Operations**  
   - **Filter** VIP clients whose account sum is above the average of all clients  
   - **Map** each client to a `String` of the form `"Name – Age years"`  
   - **Reduce** to calculate the total sum of all account amounts  
   - **Collect**:  
     - Group customers by type (`VIP`, `Standard`, `New`) and count them  
     - Join names of all customers under 25 into one `String` with `joining(", ")`  
   - Use explicit `Predicate`, `Function`, `BiFunction`, `Supplier` (no inline lambdas only)  
   - Handle missing `clientType` with `Optional.orElse("Unknown")`

---

## 4. Text File Management

**Context**  
Given a text file `data.txt` with lines formatted as `name;age;city` (e.g. `Andrei Popescu;35;Bucharest`).

**Specific Requirements**  
1. **Reading & Modeling**  
   - Read `data.txt` using `BufferedReader`  
   - Map each line to a `Person` object (`String name`, `int age`, `String city`)  
2. **Stream Operations**  
   - **Filter** people over 30 living in cities starting with `"B"`  
   - **Group** people by `city`  
   - **Aggregate** average age by `city`  
   - **Sort** people by `name`, then by `age`  
   - **Reduce** to find the oldest person  
3. **Writing Results**  
   - Write to `result.txt`:  
     - The filtered & sorted list of persons  
     - For each city: average age and number of residents  
4. **Error Handling**  
   - Use _try-with-resources_ and handle all I/O exceptions gracefully

---

> **Notes**  
> - Do **not** use classical loops (`for`, `while`) for the functional requirements: prefer Stream APIs.  
> - Each exercise must compile, be placed in its own `.java` file, and follow clear, readable code structure and naming conventions.  
> - Always use `try-with-resources` for I/O.  
> - Aim for clean, maintainable, and well‐documented code.
```
