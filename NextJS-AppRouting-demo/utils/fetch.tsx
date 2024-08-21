'use client';

import { useState, useCallback } from 'react';

interface FetchComponentProps {
    url: string;
    method?: string;
    body?: any;
    headers?: Record<string, string>;
    onSuccess: (data: any) => void;
    onError: (error: any) => void;
}

const Fetch: ({url, method, body, headers, onSuccess, onError}: {
    url: any;
    method?: any;
    body: any;
    headers?: any;
    onSuccess: any;
    onError: any }) => { fetchData: () => Promise<void>; loading: boolean; error: string | null } =
    ({ url, method = 'POST', body, headers = {}, onSuccess, onError }) => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchData = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await fetch(url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    ...headers,
                },
                body: body ? JSON.stringify(body) : undefined,
            });

            if (!response.ok) {
                throw new Error('Fetch failed');
            }

            const data = await response.json();
            onSuccess(data);
        } catch (Error) {
            setError(null);
            onError(error);
        } finally {
            setLoading(false);
        }
    }, [url, method, headers, body, onSuccess, onError, error]);

    return { loading, error, fetchData };
};

export default Fetch;