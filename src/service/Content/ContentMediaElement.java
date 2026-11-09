package service.Content;

import java.util.ArrayList;
import java.util.List;

public abstract class ContentMediaElement extends ContentElement {
    private ArrayList<String> URL= new ArrayList<>();
    private String viewMedia;
    private String script;

    public void loadFromDB(String date, String time, String name, String about, String text, String script, String viewMedia, String URL, boolean approved, boolean published){
        super.loadFromDB(date, time, name, about, text, approved, published);
        this.script=script;
        this.viewMedia=viewMedia;
        this.setURL(URL);
    }


    public String getURL(){
        StringBuilder line = new StringBuilder();
        for(int i=0; i< URL.size(); i++){
            line.append(URL.get(i)).append("\n");
        }
        return line.toString();
    }
    public void setURL(String line){
        URL.clear();
        ArrayList<String> list = null;
        if(line!=null) {
            list = parseURL(line);
        }
        if(list!=null&&!list.isEmpty()){
            URL.addAll(list);
        }else{
            IO.println("Файлы не добавлены");
        }
    }

    private ArrayList<String> parseURL(String line){
        String[] paths=line.trim().split("\\s+");
        ArrayList<String> URL = new ArrayList<>(List.of(paths));
        return URL;
    }

    // Геттеры и сеттеры для viewMedia
    public void setViewMedia(String viewMedia) {
        this.viewMedia = viewMedia;
    }
    public String getViewMedia() {
        return this.viewMedia;
    }

    // Геттеры и сеттеры для script
    public void setScript(String script) {
        if ("Видео".equals(this.viewMedia) && (script.trim().isEmpty())){
            IO.println("Вы уверены что хотите сценарий для видео оставить пустым?(y/n");
            char choice = IO.readln().trim().charAt(0);
            if (choice=='n'){
                script=IO.readln().trim();
            }else{this.script=null;}
        }
        this.script = script;
    }
    public String getScript() {
        return this.script;
    }
}
