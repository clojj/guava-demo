package jwin.service;

import jwin.pojo.SomeInput;
import jwin.pojo.SomeValue;

import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class ExpensiveService {

    public SomeValue expensiveComputation(SomeInput input) throws InterruptedException {
        Thread.sleep(1000);
        Date date = new Date();
        return new SomeValue(date, Long.toString(date.getTime()));
    }
}
