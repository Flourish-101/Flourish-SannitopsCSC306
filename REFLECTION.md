# Reflection

## How did you achieve functional cohesion? Which routines did you extract?

I achieved functional cohesion by ensuring that each method performs a single responsibility. The original processCustomer() routine handled validation, calculations, message creation, output, notification, and customer updates in one method.

To improve cohesion, I extracted the following routines:

- validateCustomerData()
- calculateOrderTotal()
- calculateDiscountRate()
- buildCustomerMessage()
- sendCustomerNotification()

Each routine now focuses on one task, making the code easier to read, maintain, and test.

---

## What parameter passing issues did you encounter?

The original code attempted to update the parameter d using:

```java
d = total;