package service;

import db.DatabaseMain;
import db.ServiceDB;
import service.Content.ContentElement;
import service.Content.MediaPost;
import service.Content.TextPost;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {
    Scanner scanner=new Scanner(System.in);
    ServiceDB postManagerDB= new ServiceDB();
    public void run(DatabaseMain db) {
        boolean status=true;
        char choice;
        ContentManager contentPlan = new ContentManager();
        postManagerDB.loadAll(contentPlan, db);
        do {
            IO.println("\t\tМеню работы Content Verst версия java/0.0.1\n" +
                    "1. Посмотреть список контента\n" +
                    "2. Посмотреть архив постов\n" +
                    "3. Посмотреть согласованные посты\n" +
                    "4. Посмотреть несогласованные посты\n"+
                    "0. ВЫЙТИ");

            choice=IO.readln().trim().charAt(0);
            switch (choice){
                case '0':{status=false; break;}
                case '1':{contentPlan.getAllList();editContentPlan(contentPlan, db);break;}
                case '2':{contentPlan.getPublishedList();editContentPlan(contentPlan, db);break;}
                case '3':{contentPlan.getApprovedList();editContentPlan(contentPlan, db);break;}
                case '4':{contentPlan.getNoApprovedList();editContentPlan(contentPlan, db);break;}
                default: {
                    IO.println("Внимательно изучите МЕНЮ! Ведён неправильный выбор!");
                    break;
                }
            }
        } while (status);
    }
    public void editContentPlan(ContentManager contentPlan, DatabaseMain db) {
        boolean status=true;
        char choice;
        do {
            IO.println("\t\tМеню редактирования\n" +
                    "1. Добавить пост\n" +
                    "2. Редактировать пост\n" +
                    "3. Удалить пост\n" +
                    //"4. Согласовать пост\n" +
                    //"5. Перенести пост в архив\n"+
                    "0. ВЫЙТИ");

            choice=IO.readln().trim().charAt(0);
            switch (choice){
                case '0':{status=false; break;}
                case '1':{
                    ContentElement element;
                    IO.println("Какой вид медиа?(текст/фото/видео)");
                    String media=scanner.nextLine().trim();
                    if(media.equalsIgnoreCase("текст")){
                        element = new TextPost();
                        element.create();
                    }else if(media.equalsIgnoreCase("фото")|| media.equalsIgnoreCase("видео")){
                        element = new MediaPost();
                        element.create();
                    }else {
                        IO.println("Введён неправильный вид медиа. Действие отменено!");
                        break;
                    }
                    postManagerDB.add(element, db);
                    contentPlan.add(element);
                    break;
                }
                case '2':{
                    IO.println("Введите id поста, который хотите редактировать");
                    contentPlan.edit(IO.readln(), postManagerDB);
                    break;
                }
                case '3':{
                    IO.println("Введите id поста, который хотите удалить");
                    contentPlan.remove(IO.readln(), postManagerDB);
                    break;
                }
                //case '4':{contentPlan.getNoApprovedList();}
                default: {
                    IO.println("Внимательно изучите МЕНЮ! Ведён неправильный выбор!");
                    break;
                }
            }
        } while (status);
    }
}
