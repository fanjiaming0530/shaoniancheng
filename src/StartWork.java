import temp.TankServer;

public class StartWork {
    public static void main(String[] args) {
        TankServer tankServer=new TankServer();
        tankServer.init();
        tankServer.run();
    }
}
