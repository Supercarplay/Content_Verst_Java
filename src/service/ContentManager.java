package service;

import db.DatabaseMain;
import db.ServiceDB;
import service.Content.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContentManager {
    private ArrayList<ContentElement> contentElementsList=new ArrayList<>();
    public ContentManager(){};
    /// Сортировка списка постов по дате и времени
    private void sortList(){
        Collections.sort(contentElementsList, new Comparator<ContentElement>() {
            @Override
            public int compare(ContentElement e1, ContentElement e2) {
                return e1.getDateTime().compareTo(e2.getDateTime());
            }
        });
    }

    /// Вывод на экран всего списка постов, хранящийся в БД
    public void getAllList(){
        if(contentElementsList.isEmpty()){
            IO.println("Постов нету, добавьте записи");
        }
        IO.println("\t\tСписок постов");
        int id=0;
        for(ContentElement content : contentElementsList){
            IO.println("------------------------------------");
            IO.println("ID: "+id);
            IO.println(content.render());
            id++;
        }
    }
    /// Вывод на экран списка постов, которые были опубликование
    public void getPublishedList(){
        if(contentElementsList.isEmpty()){
            IO.println("Постов нету, добавьте записи");
        }
        IO.println("\t\tСписок опубликованных постов");
        int id=0;
        for(ContentElement content : contentElementsList){
            if(content.isPublished()){
                IO.println("------------------------------------");
                IO.println("ID: "+id);
                IO.println(content.render());
            }
            id++;
        }
        IO.println("------------------------------------");
    }
    /// Вывод на экран списка постов, которые прошли согласование
    public void getApprovedList(){
        if(contentElementsList.isEmpty()){
            IO.println("Постов нету, добавьте записи");
        }
        IO.println("\t\tСписок согласованных постов");
        int id=0;
        for(ContentElement content : contentElementsList){
            if(content.isApproved()){
                IO.println("------------------------------------");
                IO.println("ID: "+id);
                IO.println(content.render());
            }
            id++;
        }
        IO.println("------------------------------------");
    }
    /// Вывод на экран списка, который не был согласован
    public void getNoApprovedList(){
        if(contentElementsList.isEmpty()){
            IO.println("Постов нету, добавьте записи");
        }
        IO.println("\t\tСписок несогласованных постов");
        int id=0;
        for(ContentElement content : contentElementsList){
            if(!content.isApproved() && !content.isPublished()){
                IO.println("------------------------------------");
                IO.println("ID: "+id);
                IO.println(content.render());
            }
            id++;
        }
        IO.println("------------------------------------");
    }
    /// Добавление элемента в память устройства
    public void add(ContentElement element){
        contentElementsList.add(element);
        sortList();
    }
    /// Удаление элемента из памяти устройства
    public void remove(String id, ServiceDB postManagerDB) {
        try{
            contentElementsList.remove(Integer.parseInt(id));
            ContentElement element = contentElementsList.get(Integer.parseInt(id));
            postManagerDB.delete(element);
            sortList();
        }catch (Exception _){
            new ContentManagerException();
            return;
        }

    }
    /// Редактирование элемента в памяти устройста
    public void edit(String id, ServiceDB postManagerDB){
        int index=-1;
        try {
            index=Integer.parseInt(id);
        }catch (Exception _){
            new ContentManagerException();
            return;
        }
        if (index < 0 || index >= contentElementsList.size()) {
            IO.println("Ошибка: элемент с индексом " + index + " не найден. Доступно элементов: " + contentElementsList.size());
            return;
        }

        ContentElement element = contentElementsList.get(index);
        IO.println("Редактирование: " + element.getName());
        element.edit();
        postManagerDB.update(element);
        sortList();
    }
}
