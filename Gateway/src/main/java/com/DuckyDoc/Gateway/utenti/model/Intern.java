package com.DuckyDoc.Gateway.utenti.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Intern {
    private int credits;


public int getCredits(){
    return this.credits;
}

}