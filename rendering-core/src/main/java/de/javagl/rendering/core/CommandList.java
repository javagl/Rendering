/*
 * www.javagl.de - Rendering
 * 
 * Copyright 2010-2016 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.rendering.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of a suppliers for lists of {@link Command} objects,
 * which is backed by a simple list.
 */
public final class CommandList implements Supplier<List<Command>>
{
    /**
     * The List backing this {@link CommandList}
     */
    private final List<Command> commands;
    
    /**
     * Creates a new, empty {@link CommandList}
     */
    public CommandList()
    {
        commands = new ArrayList<Command>();
    }
    
    /**
     * Adds the given {@link Command} to this list
     * 
     * @param command The {@link Command} to add
     * @return This list
     */
    public CommandList add(Command command)
    {
        this.commands.add(command);
        return this;
    }
    
    /**
     * Add the given {@link Command}s to this list
     * 
     * @param commands The {@link Command}s to add
     */
    public void addCommands(Command ... commands)
    {
        this.commands.addAll(Arrays.asList(commands));
    }

    /**
     * Add the given {@link Command}s to this list
     * 
     * @param commands The {@link Command}s to add
     */
    public void addCommands(Collection<? extends Command> commands)
    {
        this.commands.addAll(commands);
    }

    /**
     * Remove the given {@link Command}s from this list
     * 
     * @param commands The {@link Command}s to remove
     */
    public void removeCommands(Command ... commands)
    {
        this.commands.removeAll(Arrays.asList(commands));
    }
    
    /**
     * Remove the given {@link Command}s from this list
     * 
     * @param commands The {@link Command}s to remove
     */
    public void removeCommands(Collection<? extends Command> commands)
    {
        this.commands.removeAll(commands);
    }
    
    @Override
    public List<Command> get()
    {
        return Collections.unmodifiableList(commands);
    }
    
    @Override
    public String toString()
    {
        return createInfoString();
    }
    
    
    /**
     * Creates an info String with all commands. <br> 
     * <br> 
     * <b>This method is only for debugging, 
     * and not part of the official API!</b>
     * 
     * @return An info String.
     */
    public String createInfoString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CommandList:\n");
        for (Command command : commands)
        {
            sb.append("    "+String.valueOf(command)+"\n");
        }
        return sb.toString();
    }
    
    
}