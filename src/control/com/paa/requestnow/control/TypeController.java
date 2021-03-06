package com.paa.requestnow.control;

import com.paa.requestnow.model.data.Category;
import com.paa.requestnow.model.data.Core;
import com.paa.requestnow.model.data.Type;

/**
 * @author artur
 */
public class TypeController
    extends 
        AbstractController<TypeController, Core>
{
    private static TypeController instance;
    
    private TypeController(){}
    
    /**
     * 
     * @return 
     */
    public static TypeController getInstance()
    {
        if( instance == null )
        {
            instance = new TypeController();
        }
        
        return instance;
    }
    
    /**
     *
     * @param type
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public String onDelete( Core type ) throws Exception
    {
        if( type != null && type.getState() == Type.STATE_INACTIVE )
        {
            return "Tipo de Requisição já encontra-se excluida!";
        }

        else if( type == null )
        {
            return "Selecione um tipo de requisição para excluir!";
        }

        else if ( com.paa.requestnow.model.ModuleContext.getInstance().getTypeManager().hasDependences( type ) )
        {
             return "Existem campos ou rotas referênciados neste tipo!";
        }
        
        return null;
    }
    
    @Override
    public String onEdit( Core type ) throws Exception
    {
        if( type == null)
        {
            return "Selecione um tipo de requisião para editar";
        }
        
        return null;
    }
    
    @Override
    public String onAdd( Core item )throws Exception
    {
        if( item == null)
        {
            return "Selecione um item iserir tipos";
        }
        
        if ( ! ( item instanceof Type ) &&
             ! ( item instanceof Category ) )
        {
             return "Selecione um item iserir tipos";
        }
        
        return null;
    }
    
    public Type createType( Object item )
    {
        Type type = new Type();
        
        if ( item instanceof Type )
        {
            type.setCategory( ( (Type) item ).getCategory() );
        }
        
        else if ( item instanceof Category ) 
        {
             type.setCategory( ( (Category) item ).getId() );
        }
        
        return type;
    }
}