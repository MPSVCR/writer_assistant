import React from "react";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";

type MarkdownDisplayProps = {
    markdownText: string;
};

const MarkdownDisplay: React.FC<MarkdownDisplayProps> = ({ markdownText }) => {
    return (
        <div className="prose prose-indigo max-w-none">
            <ReactMarkdown remarkPlugins={[remarkGfm]}>
                {markdownText}
            </ReactMarkdown>
        </div>
    );
};

export default MarkdownDisplay;
