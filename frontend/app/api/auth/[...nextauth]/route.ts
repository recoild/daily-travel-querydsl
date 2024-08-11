import NextAuth, { AuthOptions } from "next-auth";
import { JWT, JWTDecodeParams } from "next-auth/jwt";
import KeycloakProvider, {
    KeycloakProfile,
} from "next-auth/providers/keycloak";
import { OAuthConfig } from "next-auth/providers/oauth";
import { decode } from "next-auth/jwt";

declare module "next-auth/jwt" {
    interface JWT {
        id_token?: string;
        provider?: string;
    }
}

export const authOptions: AuthOptions = {
    providers: [
        KeycloakProvider({
            clientId: process.env.KEYCLOAK_CLIENT_ID,
            clientSecret: process.env.KEYCLOAK_CLIENT_SECRET,
            issuer: process.env.KEYCLOAK_ISSUER,
        }),
    ],
    callbacks: {
        async jwt({ token, user, account, profile }) {
            if (account) {
                token.id_token = account.id_token;
                token.provider = account.provider;
            }

            return token;
        },
        async session({ session, token }) {
            session.user = {
                ...session.user,
            };

            return session;
        },
    },
    events: {
        async signIn({ user, account, profile }) {
            console.log("Sign in", account?.access_token);
            if (account) {
                try {
                    console.log("Fetching /hello with access token");
                    const response = await fetch(
                        process.env.RESOURCE_SERVER_URL + "/hello",
                        {
                            headers: {
                                Authorization: `Bearer ${account.access_token}`,
                            },
                            cache: "no-store",
                        }
                    );
                    console.log(response.status);
                    console.log(await response.text());
                } catch (e) {
                    console.error(e);
                }
            }
        },
        async signOut({ token }: { token: JWT }) {
            if (token.provider === "keycloak") {
                const issuerUrl = (
                    authOptions.providers.find(
                        (p) => p.id === "keycloak"
                    ) as OAuthConfig<KeycloakProfile>
                ).options!.issuer!;
                const logOutUrl = new URL(
                    `${issuerUrl}/protocol/openid-connect/logout`
                );
                logOutUrl.searchParams.set("id_token_hint", token.id_token!);

                console.log("Logging out from Keycloak", logOutUrl.toString());
                await fetch(logOutUrl);
            }
        },
    },
};
const handler = NextAuth(authOptions);
export { handler as GET, handler as POST };
