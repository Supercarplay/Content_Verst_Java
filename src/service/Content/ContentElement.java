package service.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public abstract class ContentElement {
    Scanner scanner=new Scanner(System.in);

    private Integer idDB;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String about;
    private String text;
    private boolean approved;
    private boolean published;

    // Абстрактные методы для полиморфизма
    public abstract String render();
    public abstract void edit();
    public void create() {
        IO.println("Введите название:");
        setName(scanner.nextLine().trim());

        IO.println("Введите дату (ДД.ММ.ГГГГ):");
        setDate(IO.readln().trim());

        IO.println("Введите время (ЧЧ:ММ):");
        setTime(IO.readln().trim());

        IO.println("Введите описание:");
        setAbout(scanner.nextLine().trim());

        IO.println("Введите текст:");
        setText(scanner.nextLine().trim());
    }

    public void loadFromDB(String date, String time, String name, String about, String text, boolean approved, boolean published){
        this.setDate(date);
        this.setTime(time);
        this.name=name;
        this.about=about;
        this.text=text;
        this.approved=approved;
        this.published=published;
    }

    // Геттеры и сеттеры для date
    public void setDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            System.out.println("Ошибка: дата не может быть пустой. Устанавливаем сегодняшнюю дату");
            this.date=LocalDate.now();
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            this.date = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка формата даты. Используйте формат DD.MM.YYYY (например, 03.11.2026)");
        }
    }
    public String getDate() {
        return this.date != null ? this.date.toString() : null;
    }


    // Геттеры и сеттеры для time
    public void setTime(String timeStr) {
        if (timeStr==null||timeStr.trim().isEmpty()){
            System.out.println("Ошибка: время не может быть пустым. Устанавливаем нынешнее время");
            this.time=LocalTime.now();
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            this.time = LocalTime.parse(timeStr, formatter);
        } catch (Exception e) {
            System.out.println("Ошибка формата времени. Используйте HH:mm");
        }
    }
    public String getTime() {
        return this.time != null ? this.time.toString() : null;
    }

    /// Общий геттер для даты и времени
    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.date, this.time);
    }

    // Геттеры и сеттеры для name
    public void setName(String name) {
        if(name==null||name.trim().isEmpty()){
            IO.println("Вы уверены что хотите название оставить пустым?(y/n");
            char choice = IO.readln().trim().charAt(0);
            if (choice=='n'){
                name=IO.readln().trim();
            }else{this.name=null;}
        }
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    // Геттеры и сеттеры для about
    public void setAbout(String about) {
        if(about==null||about.trim().isEmpty()){
            IO.println("Вы уверены что хотите описание оставить пустым?(y/n");
            char choice = IO.readln().trim().charAt(0);
            if (choice=='n'){
                about=IO.readln().trim();
            }else{this.about=null;}
        }
        this.about = about;
    }
    public String getAbout() {
        return this.about;
    }

    // Геттеры и сеттеры для text
    public void setText(String text) {
        if(text==null||text.trim().isEmpty()){
            IO.println("Вы уверены что хотите текст оставить пустым?(y/n");
            char choice = IO.readln().trim().charAt(0);
            if (choice=='n'){
                text=IO.readln().trim();
            }else{this.text=null;}
        }
        this.text = text;
    }
    public String getText() {
        return this.text;
    }

    // Геттеры и сеттеры для approved
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public boolean isApproved() {
        return this.approved;
    }

    // Геттеры и сеттеры для published
    public void setPublished(boolean published) {
        this.published = published;
    }
    public boolean isPublished() {
        return this.published;
    }

    // Геттеры и сеттеры для idDB
    public void setIdDB(Integer id) { this.idDB = id; }
    public Integer getIdDB() { return this.idDB; }
}