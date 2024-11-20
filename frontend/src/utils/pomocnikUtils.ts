export function isUUID(id: string): boolean {
    const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
    return uuidRegex.test(id);
}

export function setHintProperly(hint: string, text: string): string {
    let newHint = ''

    if (hint) {
        const withoutQMarks = hint.replace(/^['"]|['"]$/g, '')
        newHint = withoutQMarks.replace(text, '')
    }

    return newHint
}

export function addSpaceIfNeeded(hint: string, text: string): string {
    let newHint = ''
    if (hint) {
        const specialCharacters = /^[.,\-?!:/+—]/;

        if (hint.startsWith(' ') || specialCharacters.test(hint) || text.endsWith(' ')) {
            return hint;
        }

        newHint = ' ' + setHintProperly(hint, text);
    }

    return newHint
}