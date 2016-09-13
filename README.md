# crashlogs
Used for custom crash handler, which we will receive by email

In your base activity add this code in onCreate().

```
        String email = "";
        CustomException.initialize(this,null,email);
```




