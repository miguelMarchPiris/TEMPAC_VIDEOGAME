package edu.ub.pis2019.pis_16.tempac.Model

interface Factory {
    fun create(temperature: Int): Ghost
}