
export default function Team({label, name, setName, points, setPoints}) {

    //const [points, setPoints] = useState(0);
    //const [name, setName] = useState("");

    function onClickButtonEvent(e) {
        const value = Math.floor(Math.random() * 3) + 1;
        setPoints(points + value);
    }

    return (
        <>
            <label>Home Team</label>
            <input
                type="text"
                value={name}
                onChange={e => {setName(e.target.value)}} 
            />   
            <label>Points</label>
            <input
                type="number"
                value={points}
                readOnly 
            />
            <input
                type="button"
                value="Throw"
                onClick={ () => onClickButtonEvent()} 
            />
        </>

    )


}