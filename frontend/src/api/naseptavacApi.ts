import {isUUID} from "../utils/pomocnikUtils.ts";
import {CreateDocumentDto} from "../model/createDocumentDto.ts";


export const getData = async (sessionId: string, text: string, model: string = "naseptavac_v1", personId: number = 1) => {
    if (!isUUID(sessionId)) {
        throw new Error('Invalid UUID format for id');
    }

    const params = new URLSearchParams({
        model: model,
        personId: personId.toString(),
    });

    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL}/createDocument?${params.toString()}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'tralalacek': sessionId,
        },
        body: JSON.stringify(text)
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`);
    }


    const dto = await response.json()
    console.log(dto)
    return (dto as CreateDocumentDto).choices;

};
