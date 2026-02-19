export default function Button({type, value, handleClick}) {
    return (
        <>
            <input type={type} value={value} onClick={handleClick} />
        </>
    )
}
