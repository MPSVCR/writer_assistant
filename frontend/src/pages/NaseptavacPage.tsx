import NaseptavacTextArea from "../components/NaseptavacTextArea.tsx";

type Props = {
    model: string
}

const NaseptavacPage = ({model}: Props) => {
    return (
        <div className="h-full flex flex-col">
            <NaseptavacTextArea model={model} />
        </div>
    )
}

export default NaseptavacPage