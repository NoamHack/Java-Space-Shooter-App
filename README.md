# משחק אנדרואיד 'Space Shooter' 👾👾👾

## סקירה כללית
אפליקציית משחק זו לאנדרואיד היא משחק על מנת לשרוד את התקפות האויב. המשחק כולל מערכת ניקוד, ספירת חיים, תנועה דינאמית של חלליות האויב, מכניקות ירי של השחקן ואפקטים ויזואליים מתפוצצים. הוא פותח ב-Java, כחלק ממסגרת Android SDK, ומיועד לאינטראקציה עם מסך מגע.

המשחק גם משלב Firebase לפעולות בצד השרת, ומספק שירותים כמו לוח ניקודים, השמעת קבצי מוזיקה, אימות לאזורי משתמשים ופונקציונליות ספציפיות למנהלים, מה שמעצים את ההתמקדות של המשתמשים ואת נוחות הניהול של האפליקציה.

## הפעלת המשחק באופן מקומי
כדי להגדיר ולהפעיל את המשחק הזה במחשב מקומי, עקוב אחר השלבים הבאים:

  **הגדרת סביבה:**
  
     התקן את Android Studio.
     
     השתמש ב- AVD Manager להכנת אמולטור מכשיר אנדרואיד.
     
     ודא שמאגר Maven של Google כלול בקבצי gradle של הפרויקט שלך כדי להשתמש ב-Firebase.

   **שילוב Firebase:**
   
     נווט אל Tools > Firebase בתוך Android Studio כדי להגדיר את פרויקט Firebase שלך.
     
     עקוב אחר הוראות העוזר המופיעות על המסך כדי להתחבר Firebase לאפליקציה שלך.
     
     הוסף קטעי קוד נדרשים כדי להפעיל שירותי Firebase כמו אימות, Firestore לניקודים ו-Firebase Storage לניהול מוזיקה ונכסים.

   **ביצוע המשחק:**
   
     יבא את קוד הפרויקט לתוך Android Studio.
     
     סנכרן את הפרויקט עם קבצי gradle.
     
     בנה את הפרויקט כדי לפתור תלות כלשהי.
     
     הפעל את הפרויקט באמצעות האמולטור שהוגדר או מכשיר אמיתי דרך `adb`.

   **הגדרת שירותי Firebase:**
   
     הגדר אימות Firebase לפונקציונליות של אזור המשתמשים.
     
     הגדר את Firebase Realtime Database או Firestore ללוח ניקודים ולניקוד השחקנים.
     
     העלה קבצי מוזיקה ל-Firebase Storage לשימוש במשחק.
     
     מנה זכויות מנהל בקונסול Firebase לאזורים ספציפיים למנהלים.

## מה במשחק?

  **מערכת ניקוד:** כל פגיעה מוצלחת בחללית אויב מגדילה את ניקוד השחקן.
  
  **ספירת חיים:** השחקנים מתחילים עם שלושה חיים, עם אחד שאבד בכל פגיעה מאויב.
  
  **מכניקות האויב:** חלליות האויב נעות בתנועה דינאמית ויורות באקראי, עם סוגים שונים של יריות.
  
  **מכניקות ירי של השחקן:** השחקן יכול לירות על ידי נגיעה במסך, שיוצרת ירייה הנעה כלפי מעלה לפגוע בחלליות האויב.
  
  **התפוצצויות:** אפקטים ויזואליים של התפוצצות מוצגים כאשר השחקן או האויב נפגעים.
  
  **אזור משתמש & אזור מנהל**: אימות Firebase מפריד בין פונקציונליות של משתמש למנהל, כגון צפייה בניקודים או ניהול הגדרות המשחק.
  
## שיקולים נוספים

  ודא כי במכשיר/אמולטור יש Google Play Services מותקן לתפקוד נכון של Firebase.
  
  הגדר חוקי Firebase נכונים לפעולות קריאה/כתיבה של נתוני משתמש כדי לשמור על ביטחון.
  
  יש לגשת לאזורי מנהלים באופן מאובטח כדי למנוע שינויים לא מורשים.
