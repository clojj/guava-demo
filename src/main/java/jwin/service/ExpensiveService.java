package jwin.service;

import jwin.pojo.SomeInput;

import javax.ejb.Stateless;

@Stateless
public class ExpensiveService {

    public SomeInput expensiveComputation(SomeInput input) throws InterruptedException {
        Thread.sleep(1000);
        return new SomeInput(input.getId(), input.getCmd(), input.getInput());
    }
}
