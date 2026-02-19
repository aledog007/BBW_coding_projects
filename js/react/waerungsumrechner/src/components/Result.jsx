export default function Result({title, value}) {
    return (
        <>
            <h2>{title}</h2>
            <textarea   
                rows="10"
                cols="40"
                type="div"
                value={value}
                readOnly
                />
        </>
    )
}