package com.minehut.mgm.module;

import com.minehut.mgm.match.Match;

import java.util.ArrayList;

/**
 * Created by luke on 5/30/15.
 */
public interface ModuleBuilder {

    public ArrayList<Module> load(Match match);
}
