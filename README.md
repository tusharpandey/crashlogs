# crashlogs
Used for custom crash handler, which we will receive by email

In your base activity add this code in onCreate().

```
        // requires only email.
        String email = "";
        CustomException.initializeWithData(this,null,email);
```

In this you can also pass, Your Custom Email Option to use Emial subjects as custom.


```
                // Requires custom email option.
                EmailOptions emailOptionsTemp = new EmailOptions.Builder()
                        .setEmail(email)
                        .setActivity(activity)
                        .showShortEmailTitle()
                        .build();
                        
                CustomException.initialize(emailOptionsTemp);
                
```


