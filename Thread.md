Thread
Methods execute on the thread that calls them.

Thread is created by jvm , speicfically the osSchedualr every os has its own OsSchedular which comes under the JVM

.start() -> So when we write .start() then only thread is created now the thread does not create immidiately or it can be created immidiately it completely depends upon the jvm, and .start() and the .run() is totally differnt start()  create new thread and run() will only be running a methdos in die that htread which ahs been ovberiide run() method  

RUNNABLE (thread state) -> when we write .start() the thread is is in RUNNABLE state after only if thread is actively executing code it will still called as a RUNNABLE state

Each thread get its own stack , heap is common memory amongs the all the thread the reason is stack has prameters, object referecne also the method returns so it should be return to where it called and two opject should not mix with teach other as well so each method get tis own stack for thread 

in java we can say that the fist thread which executes is main thread which run the main method i mean in application in java applcaition by default jvm start lot of thread like compiler thread , gc thread but they are for background in java applcaitionwe can say taht main thread is the first thred which we start the firstly

.join() means let say you have created t1 thread and in and you calll t1.join() so in which place you call t1.join() that thread will wait until t1 gets finsh the execution below example the main thread will stop untill the t1 finish the execution SO THE CURRENT THREAD WHICH HAS CALLED THE METHOD OF JOIN IS IN WAITING STATE 

========================================================================================================================================================
public static void main(String[] args) throws Exception {

    Thread t1 = new Thread(() -> {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

        System.out.println("Task Completed");
    });

    t1.start();

    t1.join();

    System.out.println("4.OperatorsALL.OperatorsALL.Main Finished");
}
========================================================================================================================================================


USER THREAD VS DAEMON THREAD (background helper thread)

========================================================================================================================================================
Thread t1 = new Thread(() -> {
    while (true) {
    }
});

t1.start();

System.out.println("4.OperatorsALL.OperatorsALL.Main Finished");
========================================================================================================================================================
what will happen in abvoe case we have two hread main thread and the t1 thread right, main thread will execute and it will create t1 object and then 
when you main thread will execute t1.start() jvm will create a new thread inside  thread the piece of code that we have writtern will start to execute right so the code is saying while true execute so it will not stop becuase we have two thread first one i main thread and the secodn one is t1 thread main thread will finsh its exectuion it wiill stop but not the t1 thread  it will continously running right so t1 will run the froever.
so JVM WILL NOT EXIT. it will keep jvm alive. Jvm cehcks if any user create thread is alvie if yes then it will not exit so we ahave two types of thread user thread and daemon thread we can do one thing 
========================================================================================================================================================
Thread t = new Thread(() -> {
    while (true) {
    }
});

t.setDaemon(true);

t.start();

System.out.println("4.OperatorsALL.OperatorsALL.Main Finished");
========================================================================================================================================================
here in above case main thread will finished after execution and then jvm will cehck if there is user created thread is there so in above case it will be not so it will exit by killing daemon thread dameon thread will WorkerQueueListnerMain is user created thread they are ment to be run forever then handle lots of thing like Listen to zeromq, send heartbeat,discover nodes,process schedular request 
