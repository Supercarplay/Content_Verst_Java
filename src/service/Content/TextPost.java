package service.Content;

import java.io.IOException;

public class TextPost extends ContentElement {
    //Конструкторы
    public TextPost() {}
    private TextPost(String date, String time, String name, String about, String text) {
        this.setDate(date);
        this.setTime(time);
        this.setName(name);
        this.setAbout(about);
        this.setText(text);
        this.setApproved(false);
        this.setPublished(false);
    }
    private TextPost(String date, String time, String name, String about, String text, boolean approved, boolean published) {
        this.setDate(date);
        this.setTime(time);
        this.setName(name);
        this.setAbout(about);
        this.setText(text);
        this.setApproved(approved);
        this.setPublished(published);
    }

    @Override
    public void edit(){
        IO.println("Что хотите отредактировать?");
        char choiceEdit;
        boolean status=true;
        do {
            IO.println("\t\tМеню редактирования поста\n" +
                    "1. Дату\n" +
                    "2. Время\n" +
                    "3. Название\n" +
                    "4. Описание\n" +
                    "5. Текст\n" +
                    "6. Согласовать/несогласовать\n" +
                    "7. Внести в архив/вынести из архива\n" +
                    "0. Выйти");
            choiceEdit = IO.readln().trim().charAt(0);
            switch (choiceEdit) {
                case '0':{status=false; break;}
                case '1':{
                    IO.println("Введите новую дату в формате дд.мм.гггг");
                    setDate(IO.readln());
                    break;
                }
                case '2':{
                    IO.println("Введите новое время в формате HH:MM");
                    setTime(IO.readln());
                    break;
                }
                case '3':{
                    IO.println("Введите новое название");
                    setName(scanner.nextLine());
                    break;
                }
                case '4':{
                    IO.println("Введите новое описание");
                    setAbout(scanner.nextLine());
                    break;
                }
                case '5':{
                    IO.println("Введите новый текст");
                    setText(scanner.nextLine());
                    break;
                }
                case '6':{
                    if(isApproved()){
                        setApproved(false);
                    }else{setApproved(true);}
                    break;
                }
                case '7':{
                    if(isPublished()){
                        setPublished(false);
                    }else{setPublished(true);}
                    break;
                }
                default:{
                    IO.println("Неверное значение меню!");
                    break;
                }
            }
        }while (status);
    }

    @Override
    public void create(){
        super.create();


        IO.println("Элемент контента создан!");
    }

    @Override
    public String render(){
        return "Дата: "+this.getDate()+" "+this.getTime() + "\nНазвание: "+ this.getName()+"\nОписание: "+this.getAbout()+"\nТекст: "+this.getText();
    }
}
