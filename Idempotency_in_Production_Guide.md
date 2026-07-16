# Idempotency in Production

## What is Idempotency?

**Definition:** An operation is idempotent if executing the **same
logical request** multiple times has the **same final effect** as
executing it once.

> **Key idea:** Idempotency protects against **retries**, not against
> **new user actions**.

## Why do we need it?

In production, duplicate requests happen because of: - Network timeout
(server processed request but client never received response) -
Automatic client retries - Load balancer/API gateway retries - Message
queue redelivery (Kafka, RabbitMQ, SQS) - Mobile network instability

Without idempotency: - Duplicate payments - Duplicate orders - Duplicate
emails/SMS - Inventory deducted twice

------------------------------------------------------------------------

## Why POST is not idempotent

`POST /orders`

Every successful POST normally creates a new resource.

Request #1 → Order #101

Request #2 → Order #102

Request #3 → Order #103

Therefore POST is **not naturally idempotent**.

------------------------------------------------------------------------

# The biggest confusion (and the answer)

## ❓ If every request generates a new idempotency key, won't every request still be processed?

Yes.

That is why **you must NOT generate a new key for retries.**

Wrong:

    Retry 1 -> Key A
    Retry 2 -> Key B
    Retry 3 -> Key C

These are treated as three different operations.

Correct:

    Original Request -> Key XYZ
    Retry #1         -> Key XYZ
    Retry #2         -> Key XYZ
    Retry #3         -> Key XYZ

Now the server knows all of them represent the same logical operation.

------------------------------------------------------------------------

# Complete Production Flow

1.  User clicks **Place Order**.
2.  Frontend generates a UUID.
3.  Frontend stores it temporarily.
4.  Frontend sends:

```{=html}
<!-- -->
```
    POST /orders
    Idempotency-Key: XYZ123

5.  Backend checks storage.

If key does not exist: - Create order - Save idempotency key +
response - Return success

If network times out:

Frontend retries using **the same key**.

Backend checks:

    XYZ123 already exists?

Yes.

Backend **does not create another order**.

Instead it returns the original response.

Only one order exists.

------------------------------------------------------------------------

# Why is the key generated on the client?

The client (frontend/mobile app) knows that the second HTTP request is a
retry of the first.

The backend only sees HTTP requests. If it generated the key itself,
every retry would look like a brand-new request.

------------------------------------------------------------------------

# Does the frontend store the key?

Yes.

Usually until the operation completes successfully.

Common places: - JavaScript memory (most common) - sessionStorage -
localStorage (if refresh/recovery is needed) - Mobile local
storage/database

After success, the frontend discards the key.

------------------------------------------------------------------------

# What does the backend store?

Typical table:

  Idempotency Key   Resource     Status
  ----------------- ------------ ---------
  XYZ123            Order #101   SUCCESS

Some systems also store: - Response body - HTTP status code -
Timestamp - Expiration time

------------------------------------------------------------------------

# Race Conditions

Two identical requests may arrive at the same time.

Production solutions: - Database UNIQUE constraint on idempotency key -
Transactions - Atomic insert - Distributed lock (rare, only if needed)

Never rely on:

    check -> insert

without atomicity.

------------------------------------------------------------------------

# Redis in Production

Redis is often used as a fast lookup cache.

Flow:

Client ↓ API ↓ Redis lookup

Found? - Yes -\> Return previous response - No -\> Process request -\>
Save DB -\> Cache in Redis

Database remains the source of truth.

------------------------------------------------------------------------

# What idempotency does NOT solve

It does NOT stop intentional new requests.

Example:

User clicks Pay three separate times:

    Key A
    Key B
    Key C

These are three different operations and may all be processed.

To prevent accidental double-clicks, combine idempotency with: - Disable
button - Loading spinner - Debounce/throttle - Backend idempotency

------------------------------------------------------------------------

# Production Best Practices

-   Generate one UUID per logical operation.
-   Reuse the same key for every retry.
-   Send it in the `Idempotency-Key` header.
-   Store key + response on the server.
-   Add a UNIQUE constraint on the key.
-   Expire old keys after a business-defined period (e.g. 24--72 hours).
-   Return the original response for duplicate requests.
-   Make retry logic safe by combining retries with idempotency.

------------------------------------------------------------------------

# Interview Answers

**Q: Why is POST not idempotent?**

Because each POST typically creates a new resource.

**Q: Why is the idempotency key generated on the client?**

Because only the client knows multiple HTTP requests are retries of the
same logical operation.

**Q: What problem does idempotency solve?**

Duplicate processing caused by retries---not legitimate new requests.

**One-line summary**

> One logical operation = One idempotency key. Every retry must reuse
> that same key.
