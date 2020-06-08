/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.index;

import javax.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Alvaro
 */
@Controller
public class Indexcontroller {
    
    
    @GetMapping("")
    @PermitAll
    public ModelAndView Index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Index");
        return mv;
    }
}
