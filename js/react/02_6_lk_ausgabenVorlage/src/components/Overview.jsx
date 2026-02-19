// hier Ihren Namen eintragen
//Alessio Fano
function Overview({title, value}) {

    return (
        <>
            <div>
            <h2>{title}</h2>
            <textarea   
                rows="10"
                cols="40"
                type="div"
                value={value}
                readOnly
                />
            </div>
        </>
    )
}

export default Overview