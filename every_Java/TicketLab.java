class Ticket {
    public static final int MAX_SEATS = 50;
    private static int nextId = 1;
    private static int issuedCount = 0;
    private final int id;
    private final String owner;
    private final StringBuilder note;

    public Ticket(String owner) {
        if(owner == null) throw new IllegalArgumentException("owner must not be null: " + owner);
        if(owner.isEmpty()) throw new IllegalArgumentException("owner must not be empty: " + owner);
        if(issuedCount >= MAX_SEATS) throw new IllegalStateException("issuedCount must be smaller than MAX_SEATS: " + issuedCount);

        id = nextId;
        nextId++;
        this.owner = owner;
        this.note = new StringBuilder();
        issuedCount++;
    }

    public int getId() { return id; }

    public String getOwner() { return owner; }

    public void addNote(String text) {
        if(text == null) throw new IllegalArgumentException("text must not be null: " + text);
        note.append(text);
    }

    public String getNote() { return note.toString(); }

    public static int getIssuedCount() { return issuedCount; }

    public static void reset() {
        nextId = 1;
        issuedCount = 0;
    }

    // void renumber() { id = 99; }

    // static int peekId() { return id; }
}

public class TicketLab {
    public static void main(String[] args) {
        Ticket t1 = new Ticket("田中");
        Ticket t2 = new Ticket("鈴木");
        Ticket t3 = new Ticket("佐藤");

        System.out.println("--- 区間A ---");
        System.out.println("期待値: 1 田中  " + t1.getId() + " " + t1.getOwner());
        System.out.println("期待値: 2 鈴木  " + t2.getId() + " " + t2.getOwner());
        System.out.println("期待値: 3 佐藤  " + t3.getId() + " " + t3.getOwner());
        System.out.println("期待値: 3  " + Ticket.getIssuedCount());
        System.out.println("期待値: 50  " + Ticket.MAX_SEATS);

        System.out.println("--- 区間B ---");
        t1.addNote("窓側"); 
        t1.addNote("禁煙");
        System.out.println("t1.getNote() " + t1.getNote());
        // 予想: 窓側禁煙
        System.out.println("t2.getNote() " + t2.getNote());
        // 予想: 空白
        Ticket.reset();
        System.out.println("Ticket.getIssuedCount() " + Ticket.getIssuedCount());
        // 予想: 0
        Ticket t4 = new Ticket("高橋");
        System.out.println("t4.getId() " + t4.getId());
        // 予想: 1
        System.out.println("t1.getId() " + t1.getId());
        // 予想: 1

        System.out.println("--- 区間C ---");
        // 3行出た
        try {
            new Ticket(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Ticket("");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            t1.addNote(null);;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Ticket.MAX_SEATS = 60;

        System.out.println("--- 発展 ---");
        Ticket.reset();
        for (int i = 0; i < 50; i++) {
            new Ticket("x");
        }
        System.out.println("期待値: 50  " + Ticket.getIssuedCount());
        try {
            new Ticket("51人目");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("予想: 50  " + Ticket.getIssuedCount());      
    }
}