package springboot.projects.OrderInterface.models;

public class Products {
    
    private int id;
    private String name; 
    private int price;
    private String status;
    private String created_at;

    public Products(int id, String name, int price, String status, String created_at) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() { return created_at; }

    public void setCreated_at(String created_at) { this.created_at = created_at; }
}
