# 自定Annotation #

對於一個java bean，想為每個欄位建立一個資料夾
有什麼好方法避免重複的code呢？


## 傳統方式 ##
傳統上，若有3個欄位，則建立這3個資料夾可能需要hardcode
```java
String[] folderNames = { "folder1", "folder2", "folder3" };
for(String s : folderNames) {
	// create folder
}
```
如果新增欄位，則需變更程式。但改用Annotation的方法，則更為彈性！


## Annotion + Reflection ##

* 定義Annotion
```java
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE)
public @interface FolderAnnotation {
	String folderName();
	String folderPrefix() default "";
}
```

* 使用Annotion
```java
public class UserBean {
	@FolderAnnotation(folderName = "name", folderPrefix = "userbean")
	String name;

	String email;
}
```

* 解析Annotion
```java
void createFolder(UserBean bean) {
	Field[] fields = bean.getClass().getDeclaredFields();
	for(Field f : fields) {
		FolderAnnotation ann = f.getAnnotation(FolderAnnotation.class);
		File folderFile = new File("/project/root", ann.folderPrefix(), ann.folderName());
	}
}
```