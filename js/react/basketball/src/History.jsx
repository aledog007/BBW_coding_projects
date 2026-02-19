import court from "../Court.jpg"

export default function History() {

    return (
        <>
            <img src={court} width="400" alt="Basketballplatz" /> 
            <label>History</label>   
            <ul>
                <li>Eintrag eins</li>
                <li>Eintrag zwei</li>
            </ul>
            <input type="button" value="reset" onClick={ () => console.log("Reset pressed")} />
        </>
    )
}