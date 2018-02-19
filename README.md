## Native Android Dynamic Questionnaire System
<ul>
    While I was working on a market research project, I had to develop an android app for collecting questionnaire from different persons in a mobile/tab. This includes:
    
    <pre>
    1. Single Option - Take one option from multiple options
    2. Multiple Options - Take multiple option from multiple options
    3. Verbatim - Voice for the question asked
    </pre>
</ul>

<ul>Questions can be taken from any language, for DB I have used sqlite. A sqlite file [mysql2sqlite.accdb] needs to be       stored in the storage of the device. You can add using MS Access to the file and import to mysql and then convert the     mysql to SQlite. I have also created a .net app for creating the questionnaire more easily.
    <pre>
    DB File Location: dynamic_struct_for_sqlite/mysql2sqlite.accdb
    .net App Location: dynamic_struct_for_sqlite/mysql2sqlite.accdb
    SQLite File to be placed in SD card: mysql2sqlite.db
    </pre>    
</ul>

<ul>
    I have also implemented MVC pattern also for better manageability. MainActivity.java specifies all the functions required to run the app and Model_db.java consists of all the DB location and related queries. App related strings are placed in the strings.xml so that anyone can easily change.
    <pre>
    MainActivity.java - Controller
    Model_db.java - Model
    res/layout/activity_front.xml - Layout for First Front View & Information Fillup
    res/layout/activity_main.xml - Layout for Questionnaire
    res/values/strings.xml - App related strings in one place
    </pre>
</ul>

## Configure ADB and run from your device
    Connect the device and show the google usb driver. Once devices is recognized you will be able to see the device with first command. After that install to your device with the following command. Or you can also copy the sqlite file and install from mobile.
    <pre>
    adb devices
    adb -e install -r "path-to-apk"
    </pre>