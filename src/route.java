public class route {
    public route(int bus_id,String deprt, String arrive, String deprt_at, String arrive_by, int fare,int seats) {
        Deprt = deprt;
        Arrive = arrive;
        Deprt_at = deprt_at;
        Arrive_by = arrive_by;
        this.fare = fare;
        this.bus_id = bus_id;
        this.seats=seats;
        getRoute();
        System.out.println("hello");
    }
    public void getRoute()
    {
        System.out.println(Deprt+" "+Arrive+" "+Deprt_at+" "+Arrive_by+" "+fare+" "+bus_id);
    }
    
    String Deprt;
    String Arrive;
    String Deprt_at;
    String Arrive_by;
    int fare;
    int bus_id;
    int seats;
    String date;
    
}
