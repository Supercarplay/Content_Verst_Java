package service.Content;

import service.Content.*;

public class MediaPost extends ContentMediaElement{
    //Конструкторы
    public MediaPost(){}
    private MediaPost(String date, String time, String name, String about, String text, String script, String viewMedia, String URL) {
        this.setDate(date);
        this.setTime(time);
        this.setName(name);
        this.setAbout(about);
        this.setText(text);
        this.setScript(script);
        this.setViewMedia(viewMedia);
        this.setURL(URL);
        this.setApproved(false);
        this.setPublished(false);
    }
    private MediaPost(String date, String time, String name, String about, String text, String script, String viewMedia, String URL, boolean approved, boolean published) {
        this.setDate(date);
        this.setTime(time);
        this.setName(name);
        this.setAbout(about);
        this.setText(text);
        this.setScript(script);
        this.setViewMedia(viewMedia);
        this.setURL(URL);
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
                    "6. Сценарий\n" +
                    "7. Медиафайлы\n" +
                    "8. Согласовать/несогласовать\n" +
                    "9. Внести в архив/вынести из архива\n" +
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
                    IO.println("Введите новый сценарий");
                    setScript(scanner.nextLine());
                    break;
                }
                case '7':{
                    IO.println("Введите новые ссылки на файлы через пробел");
                    setURL(scanner.nextLine());
                    break;
                }
                case '8':{
                    setApproved(!isApproved());
                    break;
                }
                case '9':{
                    setPublished(!isPublished());
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
        IO.println("Введите URL файлов (через пробел):");
        String urls = scanner.nextLine();
        setURL(urls);
        IO.println("Вид медиа ещё раз (фото/видео)");
        setViewMedia(scanner.nextLine());
        IO.println("Введите сценарий съёмки");
        setScript(scanner.nextLine());
        IO.println("Элемент контента создан!");
    }

    @Override
    public String render() {
        return "Дата: "+this.getDate()+" "+this.getTime() + "\nНазвание: "+ this.getName()+"\nОписание: "+this.getAbout()+
                "\nТекст: "+this.getText()+"\nСценарий: "+this.getScript()+"\nМедиа: "+this.getViewMedia()+"\nСсылки: "+getURL();
    }
}
