package az.mushfigm.bankdemoboot.schedule;

public class MyThread2 extends Thread{
    @Override
    public void run() {
        try {
            while (true) {
                getAllCustomers();
                Thread.sleep(3000);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void getAllCustomers() {
        System.out.println("Hello Revan!");
    }
}
