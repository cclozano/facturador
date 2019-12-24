package com.aurora.pos.sri.mappers;

/**
 * Created by max on 14/06/17.
 */
public interface Mapper<Entity,Comprobante>{
    Comprobante map(Entity entity);
}
