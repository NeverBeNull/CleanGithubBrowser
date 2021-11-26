package com.neverbenull.cleangithubbrowser.data.mapper

abstract class BaseMapper<DOMAIN, DATA> {
    abstract fun toDomainModel(dataModel: DATA) : DOMAIN
    abstract fun toDataModel(domainModel: DOMAIN) : DATA
}