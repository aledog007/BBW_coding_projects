export default function InputCHF({CHF, name, currency, setCurrency}) {

    return (
        <>
        <div>    
            <label>{CHF}</label>
            <input
            value={currency}
            onChange={(e) => setCurrency(e.target.value)}
            type="number"
            name={name}

            />
        </div>

        </>
    )
}