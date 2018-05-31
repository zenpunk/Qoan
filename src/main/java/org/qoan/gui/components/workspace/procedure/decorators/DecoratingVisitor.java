/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package org.qoan.gui.components.workspace.procedure.decorators;

import org.qai.parsers.antimirov.nodes.*;
import org.qoan.gui.components.common.decorators.Decorator;

import java.util.Map;
import java.util.TreeMap;

public class DecoratingVisitor implements NodeVisitor {

    private Map<String, Decorator> decoratorMap;

    public DecoratingVisitor() {
        decoratorMap = new TreeMap<>();
    }

    @Override
    public Object visit(AlternationNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ConcatenationNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(EmptyNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(IterationNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(Node node, Object data) {
        return null;
    }

    @Override
    public Object visit(NameNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(NoneNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(PrimitiveNode node, Object data) {
        return null;
    }

    public Map<String, Decorator> getDecoratorMap() {
        return decoratorMap;
    }

    public void setDecoratorMap(Map<String, Decorator> decoratorMap) {
        this.decoratorMap = decoratorMap;
    }
}
