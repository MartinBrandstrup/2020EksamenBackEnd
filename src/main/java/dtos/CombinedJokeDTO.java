/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author Brandstrup
 */
public class CombinedJokeDTO
{
    private List<ChuckDTO> chuckNorrisJokes;
    private List<DadDTO> dadJokes;

    public CombinedJokeDTO(List<ChuckDTO> chucks, List<DadDTO> dads)
    {
        this.chuckNorrisJokes = chucks;
        this.dadJokes = dads;
    }
    
    
}
