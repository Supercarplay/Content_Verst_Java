package service;

class ContentManagerException extends Exception{
    public ContentManagerException() {
            super("Пост с таким ID не найден");
    }
}