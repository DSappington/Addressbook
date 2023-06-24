package aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import data.LogFileHandler;
import pojo.Contact;

@Aspect
public class ContactAspect {
    
    private LogFileHandler logFileHandler = new LogFileHandler();

    @Pointcut("execution(* pojo.AddressBook.updateContact(..)) && args(oldContact, newContact)")
    public void updatePointcut(Contact oldContact, Contact newContact) {}

    @After("updatePointcut(oldContact, newContact)")
    public void afterUpdate(JoinPoint joinPoint, Contact oldContact, Contact newContact) {
        System.out.println("Contact Updated, Updating Log...");
        logFileHandler.writeLog("Record Updated: " + oldContact.toString(), "addressbook.log");
    }

    @Pointcut("execution(* pojo.AddressBook.deleteContact(..)) && args(contact)")
    public void deletePointcut(Contact contact) {}

    @After("deletePointcut(contact)")
    public void afterDelete(JoinPoint joinPoint, Contact contact) {
        System.out.println("Contact Deleted, Updating Log...");
        logFileHandler.writeLog("Record Deleted: " + contact.toString(), "addressbook.log");
    }

    @Pointcut("execution(* pojo.AddressBook.addContact(..)) && args(contact)")
    public void addPointcut(Contact contact) {}

    @After("addPointcut(contact)")
    public void afterAdd(JoinPoint joinPoint, Contact contact) {
          System.out.println("Contact Added.");
//		  Commented out as they were not outlined in the requirements
//        logFileHandler.writeLog("Record Added: " + contact.toString(), "addressbook.log");
    }
}
