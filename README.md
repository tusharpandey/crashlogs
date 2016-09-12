# crashlogs

Gradle Dependency  for adding CrashLogs in app.

```
compile 'com.crashlogs:crashlogs:1.1'
```

How to start implementing it.

```
  MainActivity1 extends CustomExceptionActivity
```


```
@Override
  protected void onCreate(Bundle savedInstanceState) {

        email = "tushar.2april@gmail.com";
        mEmailOptions = new EmailOptions.Builder()
                .setEmail(email)
                .setActivity(this)
                .showShortEmailTitle()
                .build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  }
```  
        
